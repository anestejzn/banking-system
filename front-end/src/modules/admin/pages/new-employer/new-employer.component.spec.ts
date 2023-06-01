import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEmployerComponent } from './new-employer.component';

describe('NewEmployerComponent', () => {
  let component: NewEmployerComponent;
  let fixture: ComponentFixture<NewEmployerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewEmployerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewEmployerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
