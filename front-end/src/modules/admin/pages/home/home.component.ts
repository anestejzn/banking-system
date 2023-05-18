import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { RegReqDataSource } from 'src/modules/shared/model/request-data-source';
import { Client, User } from 'src/modules/shared/model/user';
import { ClientService } from 'src/modules/shared/service/client-service/client.service';
import { RegistrationReqDetailsDialogComponent } from '../../components/registration-req-details-dialog/registration-req-details-dialog.component';
import { ConfirmationDialogComponent } from 'src/modules/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  adminSubscription: Subscription
  dataSource: RegReqDataSource;
  displayedColumns: string[] = ['name', 'surname', 'email', 'details', 'accept', 'reject'];
  empty: boolean = false;

  constructor(
    private clientService: ClientService,
    private dialog: MatDialog, 
    private toast: ToastrService
    ) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.adminSubscription = this.clientService
      .getAllPendingClients()
      .subscribe(
        res => {
           if (res.length === 0) {
            this.empty = true;
           } else {
            this.empty = false;
            this.dataSource = new RegReqDataSource(res);
           }
        },
        err => {
          this.empty = false;
        });
  }

  viewDetails(client: Client): void {
    const dialogRef = this.dialog.open(RegistrationReqDetailsDialogComponent, {
      data: client,
      width: '40rem',
      height: '30rem'
    });
  }

  acceptRequest(id: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '15rem'; // Set the width of the dialog
    dialogConfig.minHeight = '15rem'; // Set the height of the dialog
    dialogConfig.maxHeight = '90vh'; // Set the maximum height of the dialog
    dialogConfig.maxWidth = '90vw'; // Set the maximum width of the dialog
    dialogConfig.data = 'Are you sure that you want to accept this request?';
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.adminSubscription = this.clientService.accept(id).subscribe(
          res => {
            this.toast.success(`Request is accepted!`, 'Success!');
            this.loadData();
          },
          err => {
            this.toast.error(`Erro happened, try again later!`, 'Error happened!');
          }
        )
      }
    });
  }

  rejectRequest(id: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '15rem'; // Set the width of the dialog
    dialogConfig.minHeight = '15rem'; // Set the height of the dialog
    dialogConfig.maxHeight = '90vh'; // Set the maximum height of the dialog
    dialogConfig.maxWidth = '90vw'; // Set the maximum width of the dialog
    dialogConfig.data = 'Are you sure that you want to reject this request?';
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.adminSubscription = this.clientService.reject(id).subscribe(
          res => {
            this.toast.success(`Request is rejected!`, 'Success!');
            this.loadData();
          },
          err => {
            this.toast.error(`Erro happened, try again later!`, 'Error happened!');
          }
        )
      }
    });
  }

  ngOnDestroy(): void {
      if (this.adminSubscription) {
        this.adminSubscription.unsubscribe();
      }
  }


}
