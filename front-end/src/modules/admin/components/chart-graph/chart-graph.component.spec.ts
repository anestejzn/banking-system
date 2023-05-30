import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChartGraphComponent } from './chart-graph.component';

describe('ChartGraphComponent', () => {
  let component: ChartGraphComponent;
  let fixture: ComponentFixture<ChartGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChartGraphComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChartGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
