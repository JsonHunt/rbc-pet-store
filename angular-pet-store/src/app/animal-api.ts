import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AnimalAPI {

  constructor(
    public http: HttpClient
  ) { }

  public list() {
    return this.http.get('http://localhost:9966/petstore/pet/list');
  }

  public delete(id) {
    return this.http.delete('http://localhost:9966/petstore/pet/' + id);
  }

  public add(data) {
    return this.http.post('http://localhost:9966/petstore/pet', data);
  }
}
