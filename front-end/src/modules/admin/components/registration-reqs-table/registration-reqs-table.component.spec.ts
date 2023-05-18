import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationReqsTableComponent } from './registration-reqs-table.component';

describe('RegistrationReqsTableComponent', () => {
  let component: RegistrationReqsTableComponent;
  let fixture: ComponentFixture<RegistrationReqsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistrationReqsTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrationReqsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
