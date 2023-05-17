import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountTypeRegistrationComponent } from './account-type-registration.component';

describe('AccountTypeRegistrationComponent', () => {
  let component: AccountTypeRegistrationComponent;
  let fixture: ComponentFixture<AccountTypeRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountTypeRegistrationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountTypeRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
