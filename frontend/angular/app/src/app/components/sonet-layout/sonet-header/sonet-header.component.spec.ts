import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SonetHeaderComponent } from './sonet-header.component';

describe('SonetHeaderComponent', () => {
  let component: SonetHeaderComponent;
  let fixture: ComponentFixture<SonetHeaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SonetHeaderComponent]
    });
    fixture = TestBed.createComponent(SonetHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
