package com.defensorisveritatis.poloik.copacatolica2018.matches;

/**
 * Created by poloi on 04/02/2018.
 */

public class MatchesData {

    private int id;
    private String date, tb_comp, tb_home_goals, tb_away_gols, tb_id_home_club, tb_id_away_club;

    public MatchesData(int id, String tb_comp, String tb_home_goals, String tb_away_gols, String tb_id_home_club, String tb_id_away_club, String date) {
        this.id = id;
        this.tb_comp = tb_comp;
        this.tb_home_goals = tb_home_goals;
        this.tb_away_gols = tb_away_gols;
        this.tb_id_home_club = tb_id_home_club;
        this.tb_id_away_club = tb_id_away_club;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTb_comp() {
        return tb_comp;
    }

    public void setTb_comp(String tb_comp) {
        this.tb_comp = tb_comp;
    }

    public String getTb_home_goals() {
        return tb_home_goals;
    }

    public void setTb_home_goals(String tb_home_goals) {
        this.tb_home_goals = tb_home_goals;
    }

    public String getTb_away_gols() {
        return tb_away_gols;
    }

    public void setTb_away_gols(String tb_away_gols) {
        this.tb_away_gols = tb_away_gols;
    }

    public String getTb_id_home_club() {
        return tb_id_home_club;
    }

    public void setTb_id_home_club(String tb_id_home_club) {
        this.tb_id_home_club = tb_id_home_club;
    }

    public String getTb_id_away_club() {
        return tb_id_away_club;
    }

    public void setTb_id_away_club(String tb_id_away_club) {
        this.tb_id_away_club = tb_id_away_club;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
