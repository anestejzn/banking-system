import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CashCreditSecondPageComponent } from './cash-credit-second-page.component';

describe('CashCreditSecondPageComponent', () => {
  let component: CashCreditSecondPageComponent;
  let fixture: ComponentFixture<CashCreditSecondPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CashCreditSecondPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CashCreditSecondPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
