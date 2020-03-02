export class DateFormat {

  static dateAsYYYYMMDD(date: Date | string): string {
    if (typeof date === 'string') {
      return date
    }
    return date.getFullYear()
      + '-' + DateFormat.leftpad(date.getMonth() + 1, 2)
      + '-' + DateFormat.leftpad(date.getDate(), 2)
  }

  static dateAsYYYYMMDDHHNNSS(date: Date | string): string {
    if (typeof date === 'string') {
      return date
    }
    return date.getFullYear()
      + '-' + DateFormat.leftpad(date.getMonth() + 1, 2)
      + '-' + DateFormat.leftpad(date.getDate(), 2)
      + ' ' + DateFormat.leftpad(date.getHours(), 2)
      + ':' + DateFormat.leftpad(date.getMinutes(), 2)
      + ':' + DateFormat.leftpad(date.getSeconds(), 2)
  }

  private static leftpad(val, resultLength = 2, leftpadChar = '0'): string {
    return (String(leftpadChar).repeat(resultLength)
      + String(val)).slice(String(val).length)
  }

}
