package Ali_Nauta;

import Ali_Nauta.Dao.AlueDao;
import Ali_Nauta.Dao.ViestiDao;
import Ali_Nauta.Dao.ViestiKetjuDao;
import Ali_Nauta.Database.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import Ali_Nauta.Domain.Alue;
import Ali_Nauta.Domain.Viesti;
import Ali_Nauta.Domain.ViestiKetju;
import javassist.NotFoundException;
import spark.ModelAndView;

import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        String jdbcOsoite = "jdbc:sqlite:kanta.db";
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Database db = new Database("jdbc:sqlite:testi.db");
        ViestiDao viesti = new ViestiDao(db);
        ViestiKetjuDao vk = new ViestiKetjuDao(db);
        AlueDao alue = new AlueDao(db);

        get("/", (request, response) -> {
            List<Alue> alueet = alue.findAll();
            HashMap map = new HashMap<>();
            map.put("alueet", alueet);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("alue/:id", (request, response) -> {
            HashMap map = new HashMap();
            int alue_id = Integer.parseInt(request.params(":id"));
            List<ViestiKetju> viestiketjut = vk.findAllin(alue_id);

            Alue viestiKetjunAlue = alue.findOne(alue_id);

            map.put("alueId", alue_id);
            map.put("viestiketjut", viestiketjut);
            map.put("alue", viestiKetjunAlue.getOtsikko());
            map.put("alueId", viestiKetjunAlue.getId());

            return new ModelAndView(map, "ketjut");
        }, new ThymeleafTemplateEngine());

        get("alue/ketju/:id", (request, response) -> {
            HashMap map = new HashMap();
            int id = Integer.parseInt(request.params(":id"));
            List<Viesti> viestitKetjussa = viesti.findAllin(id);
            ViestiKetju viestinKetju = vk.findOne(id);
            Alue viestinAlue = alue.findOne(viestinKetju.getAlueId());

            map.put("viestiId", id);
            map.put("viestit", viestitKetjussa);
            map.put("alueenId", viestinAlue.getId());
            map.put("ketjunOtsikko", viestinKetju.getOtsikko());
            map.put("alueenOtsikko", viestinAlue.getOtsikko());
            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {
            String alueenNimi = req.queryParams("alue");
            alue.lisaaAlue(alueenNimi);

            List<Alue> alueet = alue.findAll();
            HashMap map = new HashMap<>();
            map.put("alueet", alueet);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("alue/:id", (req, res) -> {
            int alue_id = Integer.parseInt(req.params(":id"));
            String otsikko = req.queryParams("ketju");
            vk.uusiViestiKetju(alue_id, otsikko);

            HashMap map = new HashMap();
            List<ViestiKetju> viestiketjut = vk.findAllin(alue_id);
            Alue viestiKetjunAlue = alue.findOne(alue_id);

            map.put("alueId", alue_id);
            map.put("viestiketjut", viestiketjut);
            map.put("alue", viestiKetjunAlue.getOtsikko());
            map.put("alueId", viestiKetjunAlue.getId());

            return new ModelAndView(map, "ketjut");
        }, new ThymeleafTemplateEngine());

        post("alue/ketju/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            String v = req.queryParams("viesti");
            String n = req.queryParams("nimim");
            viesti.lisaaViesti(v, n, id);

            HashMap map = new HashMap();
            List<Viesti> viestitKetjussa = viesti.findAllin(id);
            ViestiKetju viestinKetju = vk.findOne(id);
            Alue viestinAlue = alue.findOne(viestinKetju.getAlueId());

            map.put("viestiId", id);
            map.put("viestit", viestitKetjussa);
            map.put("alueenId", viestinAlue.getId());
            map.put("ketjunOtsikko", viestinKetju.getOtsikko());
            map.put("alueenOtsikko", viestinAlue.getOtsikko());
            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());

        get("/404", (request, response) -> {
            throw new NotFoundException(new String("Resource not found"));
        });

        exception(NotFoundException.class, (e, request, response) -> {
            response.status(404);
            response.body(e.getMessage());
        });
    }
}