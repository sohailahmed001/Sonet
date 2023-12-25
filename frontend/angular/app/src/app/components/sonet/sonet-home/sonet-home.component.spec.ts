import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SonetHomeComponent } from './sonet-home.component';

describe('SonetHomeComponent', () => {
  let component: SonetHomeComponent;
  let fixture: ComponentFixture<SonetHomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SonetHomeComponent]
    });
    fixture = TestBed.createComponent(SonetHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
