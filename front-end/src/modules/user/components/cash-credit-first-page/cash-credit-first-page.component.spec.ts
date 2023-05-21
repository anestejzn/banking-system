import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CashCreditFirstPageComponent } from './cash-credit-first-page.component';

describe('CashCreditFirstPageComponent', () => {
  let component: CashCreditFirstPageComponent;
  let fixture: ComponentFixture<CashCreditFirstPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CashCreditFirstPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CashCreditFirstPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
