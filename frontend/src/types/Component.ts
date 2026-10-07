export enum Status {
    ACTIVE = "ACTIVE",
    INACTIVE = "INACTIVE",
    MAINTENANCE = "MAINTENANCE"
}
export interface Component {
    id: number
    name: string
    status: Status
    type : string
    lastUpdated: string
}
