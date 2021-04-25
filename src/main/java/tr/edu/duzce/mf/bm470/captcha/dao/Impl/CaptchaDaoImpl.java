package tr.edu.duzce.mf.bm470.captcha.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tr.edu.duzce.mf.bm470.captcha.dao.CaptchaDao;
import tr.edu.duzce.mf.bm470.captcha.model.Captcha;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

@Repository
public class CaptchaDaoImpl implements CaptchaDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveCaptcha(Captcha captcha) {
        sessionFactory.getCurrentSession().save(captcha);
    }

    @Override
    public List<Captcha> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Captcha> criteriaQuery = criteriaBuilder.createQuery(Captcha.class);
        Root<Captcha> root = criteriaQuery.from(Captcha.class);
        criteriaQuery.select(root);
        Query<Captcha> captchaQuery = session.createQuery(criteriaQuery);

        return captchaQuery.getResultList();
    }

    @Override
    public Captcha findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Captcha captcha = null;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Captcha> criteriaQuery = criteriaBuilder.createQuery(Captcha.class);
        Root<Captcha> root = criteriaQuery.from(Captcha.class);
        Predicate predicateId = criteriaBuilder.equal(root.get("id"), id);
        criteriaQuery.select(root).where(predicateId);
        Query<Captcha> captchaQuery = session.createQuery(criteriaQuery);
        captcha = captchaQuery.getSingleResult();

        return captcha;
    }

    @Override
    public void deleteCaptcha(Captcha captcha) {
        Session session = sessionFactory.getCurrentSession();

        Object persistentInstance = session.load(Captcha.class, captcha.getId());
        if (persistentInstance != null) {
            session.delete(persistentInstance);
        }

    }
}
