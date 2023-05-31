import { AfterViewInit, ViewChild, Component, Input, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Chart } from 'chart.js/auto';
import { Observable, Subscription, map, startWith } from 'rxjs';
import { Client, User } from 'src/modules/shared/model/user';
import { ClientService } from 'src/modules/shared/service/client-service/client.service';
import { DatePipe } from '@angular/common';
import { TransactionService } from 'src/modules/shared/service/transaction-service/transaction.service';
import { DebitService } from 'src/modules/shared/service/debit-service/debit.service';
import { ReportRequest } from 'src/modules/shared/model/report';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { ToastrService } from 'ngx-toastr';


const today = new Date();
const month = today.getMonth();
const year = today.getFullYear();

@Component({
  selector: 'app-chart-tab',
  templateUrl: './chart-tab.component.html',
  styleUrls: ['./chart-tab.component.scss']
})
export class ChartTabComponent implements AfterViewInit, OnDestroy {
  @Input() selectedReport: string;

  @ViewChild('chartCanvas') chartCanvas: any;

  dateFormGroup = new FormGroup({
    start: new FormControl(new Date(year, month, 1)),
    end: new FormControl(new Date(year, month, 25)),
  });

  allClientsObj: Client[];
  tempListOfClientEmails: string[];
  filteredClients: Observable<string[]>;
  selectedClientId: number;
  userCtrl: FormControl = new FormControl();

  chart: Chart;

  clientSubscription: Subscription;
  reportSubscription: Subscription;

  loggedUser: User;

  constructor(
    private clientService: ClientService,
    private datePipe: DatePipe,
    private transactionService: TransactionService,
    private debitService: DebitService,
    private toast: ToastrService
  ) {
    this.selectedReport = '';
    this.allClientsObj = [];
    this.tempListOfClientEmails = ['All clients'];
    this.selectedClientId = -1;
  }

  ngAfterViewInit(): void {
    this.loadAllClients();

    setTimeout(() => {
      this.createChart();
    }, 0);

    this.generateReport();
  }

  generateReport(): void {
    let request: ReportRequest = {
      clientId: this.selectedClientId,
      reportType: this.selectedReport,
      showAll: this.selectedClientId === -1,
      startDate: this.dateFormGroup.get('start').value,
      endDate: this.dateFormGroup.get('end').value
    }
    console.log(request)

    if (this.selectedReport === "CREDIT_CARD_REPORT") {
      this.reportSubscription = this.transactionService.getCreditCardReport(request).subscribe(
        res =>{
          this.chart.data.datasets[0].data[0] = res.numOfActive;
          this.chart.data.datasets[0].data[1] = res.numOfPending;
          this.chart.data.datasets[0].data[2] = res.numOfRejected;
          this.chart.update();
        },
        err => {
          this.toast.error(err.error, 'Error happened');
        }
      );
    } else {
      this.reportSubscription = this.debitService.getDebitReport(request).subscribe(
        res =>{
          this.chart.data.datasets[0].data[0] = res.numOfActive;
          this.chart.data.datasets[0].data[1] = res.numOfPending;
          this.chart.data.datasets[0].data[2] = res.numOfRejected;
          this.chart.update();
        },
        err => {
          this.toast.error(err.error, 'Error happened');
        }
      );
    }
  }

  createChart(): void {
    const canvas = this.chartCanvas.nativeElement;
    const ctx = canvas.getContext('2d');

    this.chart = new Chart("chart", {
      data: {
      labels: ['ACTIVE', 'PENDING', 'REJECTED'],
        datasets: [{
        label: 'Number of requests',
        data: [0, 0, 0],
        backgroundColor: [
          'rgba(104, 202, 68, 0.2)'
        ],
        borderWidth: 1
      }],
    },
      type: 'bar',
      options: {
        plugins: {
          title: {
            display: true,
            font: {
              size: 20
            },
            text: `Report for a period ${this.datePipe.transform(this.dateFormGroup.get('start').value, 'dd.MM.yyyy.')} to ${this.datePipe.transform(this.dateFormGroup.get('end').value, 'dd.MM.yyyy.')}`
          }
        }
      }
    });
  }

  showBySpecificUser(email: string) {
    if (email === 'All clients') {
      this.selectedClientId = -1;
    } else {
      for (const user of this.allClientsObj) {
        if (user.email === email) {
          this.selectedClientId = user.id;
        }
      }
    }

    this.generateReport();
  }

  loadAllClients(): void {
    this.clientSubscription = this.clientService.getAllVerified().subscribe(res => {
      for (const client of res) {
        this.tempListOfClientEmails.push(client.email);
        this.allClientsObj.push(client);
      }

      this.filteredClients = this.userCtrl.valueChanges.pipe(
        startWith(''),
        map(value => this._filter(value || ''))
      );
    });
  }

  _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.tempListOfClientEmails.filter(option =>
      option.toLowerCase().includes(filterValue)
    );
  }

  ngOnDestroy(): void {
      if (this.clientSubscription) {
        this.clientSubscription.unsubscribe();
      }

      if (this.chart) {
        this.chart.destroy();
      }
  }

}
