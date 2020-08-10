import {HttpErrorResponse} from '@angular/common/http'
import {of} from 'rxjs'

export function handleError(errorRes: HttpErrorResponse) {
  let errorMessage = '未知错误'
  switch (errorRes.status) {
    case 400:
      errorMessage = "内部错误"
      break
    case 401:
      errorMessage = "没有权限"
    default:
      break
  }
  return of(null)
}
