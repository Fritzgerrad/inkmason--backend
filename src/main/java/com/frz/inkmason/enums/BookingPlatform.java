package com.frz.inkmason.enums;

public enum BookingPlatform {
    whatsapp,
    phone,
    instagram,
    skype,
    Snapchat;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
