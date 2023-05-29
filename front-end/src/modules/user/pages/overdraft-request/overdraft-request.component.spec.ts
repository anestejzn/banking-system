import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverdraftRequestComponent } from './overdraft-request.component';

describe('OverdraftRequestComponent', () => {
  let component: OverdraftRequestComponent;
  let fixture: ComponentFixture<OverdraftRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverdraftRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverdraftRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
