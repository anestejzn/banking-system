import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverdraftFirstPageComponent } from './overdraft-first-page.component';

describe('OverdraftFirstPageComponent', () => {
  let component: OverdraftFirstPageComponent;
  let fixture: ComponentFixture<OverdraftFirstPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverdraftFirstPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverdraftFirstPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
