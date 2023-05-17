import { Component, OnDestroy, OnInit } from '@angular/core';
import { ControlContainer, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AccountType } from 'src/modules/shared/model/account-type';
import { AccountTypeService } from 'src/modules/shared/service/account-type-service/account-type.service';

@Component({
  selector: 'app-account-type-registration',
  templateUrl: './account-type-registration.component.html',
  styleUrls: ['./account-type-registration.component.scss']
})
export class AccountTypeRegistrationComponent implements OnInit, OnDestroy {

  public accountTypeForm: FormGroup;

  accountTypeSubscribtion: Subscription;
  allAccountTypes: AccountType[] = [];
  selected: string = '';

  constructor(private controlContainer: ControlContainer,
              private accountTypeService: AccountTypeService) {
    this.accountTypeForm = <FormGroup>this.controlContainer.control;
  }

  ngOnInit(): void {
    this.accountTypeForm = <FormGroup>this.controlContainer.control;
    this.accountTypeForm.clearValidators();
    this.accountTypeForm.updateValueAndValidity();
    this.accountTypeForm.reset();

    this.accountTypeSubscribtion = this.accountTypeService.getAll().subscribe(
      res => {
        this.allAccountTypes = res;
      }
    );
  }

  changeSelected(name: string): void {
    this.selected = name;
    this.accountTypeForm.get('accountTypeNameFormControl').setValue(name);
  }

  ngOnDestroy(): void {
      if (this.accountTypeSubscribtion) {
        this.accountTypeSubscribtion.unsubscribe();
      }
  }

}
