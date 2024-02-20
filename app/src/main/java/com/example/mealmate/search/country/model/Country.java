package com.example.mealmate.search.country.model;


import androidx.room.Entity;

@Entity(tableName = "area")
public class Country {

        private String strArea;

        public String getStrArea() {
            return strArea;
        }

        public void setStrArea(String strArea) {
            this.strArea = strArea;
        }

}
