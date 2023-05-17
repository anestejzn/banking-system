import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ControlContainer, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Employer } from 'src/modules/shared/model/employer';
import { EmployerService } from 'src/modules/shared/service/employer-service/employer.service';

@Component({
  selector: 'app-employment-registration',
  templateUrl: './employment-registration.component.html',
  styleUrls: ['./employment-registration.component.scss']
})
export class EmploymentRegistrationComponent implements OnInit, OnDestroy {
  @Input() isRetiree: boolean;
  public employmentForm: FormGroup;

  allEmployers: Employer[] = [];
  employerSubscription: Subscription;
  termsOfAgreement: boolean = false;

  constructor(private controlContainer: ControlContainer, private employerService: EmployerService) {
    this.employmentForm = <FormGroup>this.controlContainer.control;
  }
  
  ngOnInit(): void {
    this.employmentForm = <FormGroup>this.controlContainer.control;
    this.employmentForm.clearValidators();
    this.employmentForm.updateValueAndValidity();
    this.employmentForm.reset();

    this.setRequirementsOfFields();
    this.termsOfAgreement = this.employmentForm.get('termsOfPIOFondAgreementFormControl').value;

    this.employerSubscription = this.employerService.getAll().subscribe(
      res => {
        this.allEmployers = res;
      }
    );
  }

  changeAgreementTerms(): void {
    this.termsOfAgreement = !this.employmentForm.get('termsOfPIOFondAgreementFormControl').value;
    this.employmentForm.get('termsOfPIOFondAgreementFormControl').setValue(this.termsOfAgreement);
  }

  setRequirementsOfFields(): void {
    if (this.isRetiree) {
      this.employmentForm.get('termsOfPIOFondAgreementFormControl').setValidators(Validators.required);
      this.employmentForm.get('employerNameFormControl').setValidators([]);
      this.employmentForm.get('startedWorkingFormControl').setValidators([]);
    } else {
      this.employmentForm.get('termsOfPIOFondAgreementFormControl').setValidators([]);
      this.employmentForm.get('employerNameFormControl').setValidators(Validators.required);
      this.employmentForm.get('startedWorkingFormControl').setValidators(Validators.required);
    }
  }
  
  ngOnDestroy(): void {
    if (this.employerSubscription) {
      this.employerSubscription.unsubscribe();
    }
  }

}
