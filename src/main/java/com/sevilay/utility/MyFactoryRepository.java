package com.sevilay.utility;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyFactoryRepository<T, ID> implements ICrud<T, ID> {
    private Session session;
    private Transaction transaction;
    private CriteriaBuilder criteriaBuilder;
    private EntityManager entityManager;

    T t;

    public MyFactoryRepository(T entity) {
        entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
        this.t = entity;
    }

    private void openSession() {
        session = HibernateUtility.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    private void closeSession() {
        transaction.commit();
        session.close();
    }


    @Override
    public T save(T entity) {
        openSession();
        session.save(entity);
        closeSession();
        return entity;
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
        openSession();
        entities.forEach(t -> {
            session.save(t);
        });
        closeSession();
        return entities;
    }

    @Override
    public void delete(T entity) {
        openSession();
        session.delete(entity);
        closeSession();

    }

    @Override
    public void deleteById(ID id) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get("id"), id));
        T result = entityManager.createQuery(criteria).getSingleResult();
        openSession();
        session.delete(result);
        closeSession();

    }

    /**
     * SELECT * FROM tbl... WHERE id=?
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get("id"), id));
        T result = entityManager.createQuery(criteria).getSingleResult();
        return Optional.ofNullable(result);
//      T result ve return'ü yerine direkt aşağıda yorumda olan kısımları da kullanabiliriz.
//        List<T> result2 = entityManager.createQuery(criteria).getResultList();
//        if (result.isEmpty()) //Eğer sorgu neticesinde hiçbir değer dönmez ise boş olarak Optional dön
//            return Optional.empty();
//        return Optional.of(result2.get(0)); //Eğer en az bir değer dönecektir.İlk dönen değeri Optional olarak dön
    }

    @Override
    public boolean existById(ID id) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get("id"), id));
        T result = entityManager.createQuery(criteria).getSingleResult();
        return result != null; //eğer kayıt var ise(null değil ise) TRUE dön, kayıt tok ise(result null ise) FALSE dön
    }

    /**
     * SELECT * FROM tbl...
     *
     * @return
     */
    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        return entityManager.createQuery(criteria).getResultList();
    }

    /**
     * SELECT * FROM tbl.... WHERE column = value
     *
     * @param columnName
     * @param value      - % ve _ karakterleri kullanıcılar tarafından işlenir.
     * @return
     */
    @Override
    public List<T> findByColumnNameAndValue(String columnName, String value) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
//        criteria.where(criteriaBuilder.equal(root.get(columnName), value)); //Kullanıcının girdiği kolon adı ve değerin eşitliği kontrol edilir
        criteria.where(criteriaBuilder.like(root.get(columnName), value)); //Kullanıcının girdiği kolon adı ve değerin içeriğini kontrol edilir.
        return entityManager.createQuery(criteria).getResultList();
    }

    /**
     * Burada yapılmak istenilen detay şudur: Bir sınıf içindeki alan adları. (değişkenler) yazılım tarafından
     * okunacak ve bu değişkenlerin değerleri kontrol edilerek 'null olmayanlar' sorguya dahil edilecek.
     * Böylece esnek sorgulama işlemi otomatikleştirilmiş olacak.
     * Bu işlemi yapabilmek için Java Reflection Api kullanılacaktır.
     * Bu işlemin adına reverse engineering - Tersine mühendislik olarakta adlandırılabilir.
     * <p>
     * Musteri musteri = new Musteri();
     * musteri.setAd("Muhammet");
     * musteri.setSoyad("Hoca");
     * SELECT * FROM tblmusteri WHERE ad LIKE '%M%' AND soyad LIKE '%HOCA%'
     *
     * @param entity
     * @return
     */
    @Override
    public List<T> findByEntity(T entity) {
        List<T> result = null;
        Class cl = entity.getClass(); //entity nin class özelliklerini belirtiyoruz
        Field[] fl = cl.getDeclaredFields(); //class içindeki tüm değişkenleri bir liste içinde alıyoruz. id, ad, soyad vs..
        try {
            CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
            Root<T> root = (Root<T>) criteria.from(t.getClass());
            criteria.select(root);
            List<Predicate> predicateList = new ArrayList<>(); //sorgu için gerekli kriterlerin listesini ekleyeceğimiz liste

            for (int i = 0; i < fl.length; i++) {
                fl[i].setAccessible(true);
                if (fl[i].get(entity) != null && !fl[i].get(entity).equals("id")) {

                    if(fl[i].getType().isAssignableFrom(String.class))
                        predicateList.add(criteriaBuilder.like(root.get(fl[i].getName()), "%"+fl[i].get(entity)+"%"));
                    else if (fl[i].getType().isAssignableFrom(Number.class))
                        predicateList.add(criteriaBuilder.equal(root.get(fl[i].getName()), fl[i].get(entity)));
                    else predicateList.add(criteriaBuilder.equal(root.get(fl[i].getName()), fl[i].get(entity)));

                }
            }
            criteria.where(predicateList.toArray(new Predicate[]{}));//[34,343434,4343,33,4]
            result = entityManager.createQuery(criteria).getResultList();
        } catch (Exception exception) {
            System.out.println("Beklenmeyen bir hata oldu: " + exception.toString());
        }
        return result;
    }


}
