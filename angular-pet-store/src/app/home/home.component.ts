import { Component, OnInit } from '@angular/core';
import * as _ from 'lodash';
import { AppState } from '../app.service';
import { AnimalAPI } from '../animal-api';
import { XLargeDirective } from './x-large';
import { Router } from '@angular/router';

@Component({
  selector: 'home',
  providers: [
    AnimalAPI,
  ],
  styleUrls: [ './home.component.css' ],
  templateUrl: './home.component.html'
})

export class HomeComponent implements OnInit {

  constructor(
    public appState: AppState,
    public animalApi: AnimalAPI,
    public router: Router
  ) {}

  public genders = ['male', 'female'];
  public animals: [];

  public ngOnInit() {
    this.animalApi.list().subscribe((data: [any]) => this.appState.set('animals', data));
    this.animals = this.appState.get('animals');
  }

  public onAddAnimal() {
    this.router.navigate(['/new-animal']);
  }

  public onDeleteAnimal(id: string) {
    this.animalApi.delete(id).subscribe((data) => {
      let animals = this.appState.get('animals');
      _.remove(animals, function(a) { return a.id === id; });
      this.appState.set('animals', animals);
      this.animals = this.appState.get('animals');
    });
  }
}
