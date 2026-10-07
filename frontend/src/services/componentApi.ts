
import type { Component } from "../types/Component"

const API_URL = "http://localhost:8080/api/components"


export async function getAllComponents(): Promise<Component[]>{
    const response = await fetch(API_URL)

    if(!response.ok){
        throw new Error("Failed to fetch components")
    }
    return response.json()
}

export async function getComponent(id:number): Promise<Component>{
    const response = await fetch(`${API_URL}/${id}`)

    if(!response.ok){
        throw new Error("Failed to fetch component")
    }
    return response.json()
}

export async function createComponent(component: Omit<Component, "id" | "lastUpdated">):Promise <Component>{
    const response = await fetch(API_URL, {
        method: "POST",
        headers :{
            "Content-Type": "application/json"
        },
        body : JSON.stringify(component)
    })
    if(!response.ok){
        throw new Error("Failed to create component")
    }
    return response.json()
}

export async function updateComponent(component: Omit<Component, "id" | "lastUpdated">, id: number):Promise <Component>{
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers :{
            "Content-Type": "application/json"
        },
        body : JSON.stringify(component)
    })
    if(!response.ok){
        throw new Error("Failed to update component")
    }
    return response.json()
}

export async function deleteComponent(id: number): Promise<void> {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    })

    if (!response.ok) {
        throw new Error("Failed to delete component")
    }
}


