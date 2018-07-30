import {
  inject,
  async,
  TestBed,
  ComponentFixture,
  getTestBed
} from '@angular/core/testing';
import { Component } from '@angular/core';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AnimalAPI } from './animal-api';

describe('Animal API', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AnimalAPI]
    });
  });

  // it('should have http', inject([AnimalAPI], (title: AnimalAPI) => {
  //   expect(!!title.http).toEqual(true);
  // }));

  it('should get list of animals', inject([AnimalAPI], (api: AnimalAPI) => {
    // spyOn(console, 'log');
    // expect(console.log).not.toHaveBeenCalled();
    api.list().subscribe( (result) => {
      expect(result['length']).toEqual(3);
    });
  }));

});
