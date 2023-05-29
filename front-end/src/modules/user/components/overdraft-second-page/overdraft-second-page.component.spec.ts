import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverdraftSecondPageComponent } from './overdraft-second-page.component';

describe('OverdraftSecondPageComponent', () => {
  let component: OverdraftSecondPageComponent;
  let fixture: ComponentFixture<OverdraftSecondPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverdraftSecondPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverdraftSecondPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
