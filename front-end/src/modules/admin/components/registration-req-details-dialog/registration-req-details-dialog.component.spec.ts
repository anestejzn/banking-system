import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationReqDetailsDialogComponent } from './registration-req-details-dialog.component';

describe('RegistrationReqDetailsDialogComponent', () => {
  let component: RegistrationReqDetailsDialogComponent;
  let fixture: ComponentFixture<RegistrationReqDetailsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistrationReqDetailsDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrationReqDetailsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
