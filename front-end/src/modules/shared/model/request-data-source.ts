import { Observable, ReplaySubject } from "rxjs";
import {DataSource} from '@angular/cdk/collections';
import { Client } from "./user";

export class RegReqDataSource extends DataSource<Client> {
    private _dataStream = new ReplaySubject<Client[]>();
  
    constructor(initialData: Client[]) {
      super();
      this.setData(initialData);
    }
  
    connect(): Observable<Client[]> {
      return this._dataStream;
    }
  
    disconnect() {}
  
    setData(data: Client[]) {
      this._dataStream.next(data);
    }
  }