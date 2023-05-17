import { Component, OnInit } from '@angular/core';
import { ControlContainer, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-basic-info-registration',
  templateUrl: './basic-info-registration.component.html',
  styleUrls: ['./basic-info-registration.component.scss']
})
export class BasicInfoRegistrationComponent implements OnInit {
  public basicInfoForm: FormGroup;

  hidePassword = true;
  hideConfirmPassword = true;
  regularUserRoles = ['EMPLOYED', 'RETIREE'];

  constructor(private controlContainer: ControlContainer) {
    this.basicInfoForm = <FormGroup>this.controlContainer.control;
  }

  ngOnInit(): void {
    this.basicInfoForm = <FormGroup>this.controlContainer.control;
    this.basicInfoForm.clearValidators();
    this.basicInfoForm.updateValueAndValidity();
    this.basicInfoForm.reset();
  }

}
