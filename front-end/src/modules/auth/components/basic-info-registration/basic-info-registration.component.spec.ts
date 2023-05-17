import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BasicInfoRegistrationComponent } from './basic-info-registration.component';

describe('BasicInfoRegistrationComponent', () => {
  let component: BasicInfoRegistrationComponent;
  let fixture: ComponentFixture<BasicInfoRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BasicInfoRegistrationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BasicInfoRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
