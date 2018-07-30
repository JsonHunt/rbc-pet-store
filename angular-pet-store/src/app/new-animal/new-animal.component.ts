import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnimalAPI } from '../animal-api';
import { AppState } from '../app.service';

@Component({
  selector: 'new-animal',
  templateUrl: './new-animal.component.html',
  providers: [AnimalAPI]
})

export class NewAnimalComponent implements OnInit {

  constructor(
    public route: ActivatedRoute,
    public animalApi: AnimalAPI,
    public appState: AppState
  ) {}

  public localState = {
    name: '',
    gender: 'male',
    age: '',
    race: ''
  };

  public genders = ['male', 'female'];

  public cancelAnimal() {
    window.history.back();
  }

  public confirmNewAnimal() {
    const animal = {
      name: this.localState.name,
      age: this.localState.age,
      race: this.localState.race,
      gender: this.localState.gender
    };

    this.animalApi.add(animal).subscribe((data) => {
      let animals = this.appState.get('animals');
      animals.push(data);
      this.appState.set('animals', animals);
      window.history.back();
    });
  }
}
