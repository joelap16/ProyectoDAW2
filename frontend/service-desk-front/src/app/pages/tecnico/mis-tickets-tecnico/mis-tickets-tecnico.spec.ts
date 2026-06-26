import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisTicketsTecnico } from './mis-tickets-tecnico';

describe('MisTicketsTecnico', () => {
  let component: MisTicketsTecnico;
  let fixture: ComponentFixture<MisTicketsTecnico>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MisTicketsTecnico],
    }).compileComponents();

    fixture = TestBed.createComponent(MisTicketsTecnico);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
