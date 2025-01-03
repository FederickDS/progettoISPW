package org.example.entity;

import java.io.Serializable;

// Classe che rappresenta una stanza
class Room implements Serializable {
    private int roomNumber;
    private int maxPeople;
    private int floor;

    public Room() {
        /*classe bean*/
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}