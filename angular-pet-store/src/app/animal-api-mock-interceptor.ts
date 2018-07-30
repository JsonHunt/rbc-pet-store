import { Injectable } from '@angular/core';
import { of } from 'rxjs/observable/of';
import { HttpRequest,
  HttpResponse,
  HttpHandler,
  HttpEvent,
  HttpInterceptor } from '@angular/common/http';
import { assign } from 'lodash';
var nextID = 4;
var animals = [
  { id: '1', name: 'Ted',	gender: 'M', age: 12,	race: 'Bear' },
  { id: '2', name: 'Piggy',	gender: 'F', age: 5,	race: 'Pig' },
  { id: '3', name: 'Kermit',	gender: 'M', age: 7,	race: 'Frog' }
];

@Injectable()
export class MockAnimalInterceptor implements HttpInterceptor {
  public intercept(req: HttpRequest<any>, next: HttpHandler) {
    if (req.url.indexOf('localhost') === -1) {
      return next.handle(req);
    }

    if (req.url.endsWith('/pet/list') && req.method === 'GET') {
      return of(new HttpResponse({
        status: 200,
        body: animals
      }));
    }

    if (req.url.match(/\/pet\/\d+$/) && req.method === 'DELETE') {
      console.log(req);
      _.remove(animals, (a)=> a.id === req.body.id);
      return of(new HttpResponse({
        status: 200,
        body: {status: 'deleted'}
      }));
    }

    if (req.url.match(/\/pet$/) && req.method === 'POST') {
      const newAnimal = assign({ id: nextID++ }, req.body);
      animals.push(newAnimal);
      return of(new HttpResponse({
        status: 200,
        body: newAnimal
      }));
    }

    return next.handle(req);
  }
}
