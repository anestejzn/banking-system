import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatPipe implements PipeTransform {

  transform(value: string | Date, dateFormat="DD.MM.yyyy."): string {

    return moment(value).format(dateFormat);
  }

}
