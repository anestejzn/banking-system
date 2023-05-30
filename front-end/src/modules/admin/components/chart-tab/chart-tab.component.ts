import { AfterViewInit, ViewChild, Component, Input, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Chart } from 'chart.js/auto';
import { Observable, Subscription, map, startWith } from 'rxjs';
import { Client } from 'src/modules/shared/model/user';
import { ClientService } from 'src/modules/shared/service/client-service/client.service';
import { DatePipe } from '@angular/common';


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

  constructor(
    private clientService: ClientService,
    private datePipe: DatePipe
  ) {
    this.selectedReport = '';
    // this.startDate = '';
    // this.endDate = '';
    // this.chartData = null;
    // this.dateRange = '';
    // this.chart = null;
    this.allClientsObj = [];
    this.tempListOfClientEmails = ['All clients'];
    this.selectedClientId = -1;
  }

  ngAfterViewInit(): void {
    this.loadAllClients();

    setTimeout(() => {
      this.createChart();
    }, 0);
  }

  generateReport(): void {

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
