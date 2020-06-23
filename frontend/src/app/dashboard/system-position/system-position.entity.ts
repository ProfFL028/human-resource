export class SystemPosition {
  id: number;
  name: string;
  sortNumber: number = 999;
  status: boolean = true;
}

export function displaySystemPosition(systemPosition?: SystemPosition) {
  return systemPosition && systemPosition?.name ? systemPosition.name : ''
}
