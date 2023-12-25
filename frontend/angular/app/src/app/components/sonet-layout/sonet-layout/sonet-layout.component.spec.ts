import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SonetLayoutComponent } from './sonet-layout.component';

describe('SonetLayoutComponent', () => {
  let component: SonetLayoutComponent;
  let fixture: ComponentFixture<SonetLayoutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SonetLayoutComponent]
    });
    fixture = TestBed.createComponent(SonetLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
