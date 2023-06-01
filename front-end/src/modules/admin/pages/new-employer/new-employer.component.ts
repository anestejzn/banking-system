import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Employer } from 'src/modules/shared/model/employer';
import { EmployerService } from 'src/modules/shared/service/employer-service/employer.service';

const today = new Date();
const month = today.getMonth();
const year = today.getFullYear();

@Component({
  selector: 'app-new-employer',
  templateUrl: './new-employer.component.html',
  styleUrls: ['./new-employer.component.scss']
})
export class NewEmployerComponent implements OnInit {
 

  employerForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    pib: new FormControl('', [
      Validators.required,
      Validators.minLength(9),
      Validators.maxLength(9)
    ]),
    startedOperating: new FormControl(new Date(year, month, 1), [Validators.required])
  });

  constructor(private employerService: EmployerService, private toast: ToastrService) { }

  ngOnInit(): void {
  }

  addEmployer(){
    const employerRequest: Employer = {
      name: this.employerForm.get('name').value,
      pib: this.employerForm.get('pib').value,
      startedOperating: this.employerForm.get('startedOperating').value
    }

    this.employerService.save(employerRequest).subscribe(
      employer => {
        this.toast.info("New employer is added.");
      },
      error => {
        this.toast.error(error.error);
      }
    )
  }

}
