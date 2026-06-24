import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TecnicosAdmin } from './tecnicos-admin';

describe('TecnicosAdmin', () => {
  let component: TecnicosAdmin;
  let fixture: ComponentFixture<TecnicosAdmin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TecnicosAdmin],
    }).compileComponents();

    fixture = TestBed.createComponent(TecnicosAdmin);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
