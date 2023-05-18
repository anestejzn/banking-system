import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Client } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-registration-req-details-dialog',
  templateUrl: './registration-req-details-dialog.component.html',
  styleUrls: ['./registration-req-details-dialog.component.scss']
})
export class RegistrationReqDetailsDialogComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public client: Client
    ) { }

  ngOnInit(): void {
  }

}
