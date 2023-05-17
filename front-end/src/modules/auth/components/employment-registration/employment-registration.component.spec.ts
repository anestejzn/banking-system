import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmploymentRegistrationComponent } from './employment-registration.component';

describe('EmploymentRegistrationComponent', () => {
  let component: EmploymentRegistrationComponent;
  let fixture: ComponentFixture<EmploymentRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmploymentRegistrationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmploymentRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
