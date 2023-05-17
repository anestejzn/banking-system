import { Component, OnInit } from '@angular/core';
import { ControlContainer, FormGroup } from '@angular/forms';
import { Observable, map, startWith } from 'rxjs';

@Component({
  selector: 'app-address-registration',
  templateUrl: './address-registration.component.html',
  styleUrls: ['./address-registration.component.scss']
})
export class AddressRegistrationComponent implements OnInit {
  
  public addressForm: FormGroup;

  filteredCities: Observable<string[]>;

  cities: string[] = [
    'Beograd',
    'Novi Sad',
    'Kraljevo',
    'Kragujevac',
    'Jagodina',
    'Mladenovac',
    'Subotica',
    'Ruma',
    'Priboj',
    'Sabac',
    'Leskovac',
    'Vranje',
    'Smederevo',
    'Pozarevac',
    'Zrenjanin',
    'Sombor',
  ];

  constructor(private controlContainer: ControlContainer) {
    this.addressForm = <FormGroup>this.controlContainer.control;
  }

  ngOnInit(): void {
    this.addressForm = <FormGroup>this.controlContainer.control;
    this.addressForm.clearValidators();
    this.addressForm.updateValueAndValidity();
    this.addressForm.reset();
    this.filteredCities = this.addressForm
      .get('cityFormControl')
      .valueChanges.pipe(
        startWith(''),
        map(city => (city ? this._filterCities(city) : this.cities.slice()))
      );
  }

  _filterCities(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.cities.filter(city => city.toLowerCase().includes(filterValue));
  }

}
