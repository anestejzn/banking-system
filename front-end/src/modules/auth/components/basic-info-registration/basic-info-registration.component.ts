import { Component, OnInit } from '@angular/core';
import { ControlContainer, FormGroup } from '@angular/forms';
import { MyErrorStateMatcher } from '../../pages/registration/registration.component';

@Component({
  selector: 'app-basic-info-registration',
  templateUrl: './basic-info-registration.component.html',
  styleUrls: ['./basic-info-registration.component.scss']
})
export class BasicInfoRegistrationComponent implements OnInit {
  public basicInfoForm: FormGroup;

  hidePassword = true;
  hideConfirmPassword = true;
  employmentStatus = ['EMPLOYED', 'RETIREE'];

  matcher = new MyErrorStateMatcher();

  constructor(private controlContainer: ControlContainer) {
    this.basicInfoForm = <FormGroup>this.controlContainer.control;
  }

  ngOnInit(): void {
    this.basicInfoForm = <FormGroup>this.controlContainer.control;
    this.basicInfoForm.clearValidators();
    this.basicInfoForm.updateValueAndValidity();
    this.basicInfoForm.reset();
  }

  getError() {
    return this.basicInfoForm.hasError('mismatch');
  }

}
