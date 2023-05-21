import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CashCreditRequestComponent } from './cash-credit-request.component';

describe('CashCreditRequestComponent', () => {
  let component: CashCreditRequestComponent;
  let fixture: ComponentFixture<CashCreditRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CashCreditRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CashCreditRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
