package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("meetingService")
public class MeetingService {


	Session session;

	public MeetingService() {
		session = DatabaseConnector.getInstance().getSession();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = this.session.createQuery(hql);
		return query.list();
	}

	public Collection getAll(String titleValue, String sortBy, String sortOrder) {

		String hql = "FROM Meeting WHERE title LIKE :title";
		if (sortBy.equals("title")) {
			hql += " ORDER BY " + sortBy;
			if (sortOrder.equals("ASC") || sortOrder.equals("DESC")) {
				hql += " " + sortOrder;
			}
		}
		Query query = session.getSession().createQuery(hql);
		query.setParameter("id", "%" + titleValue + "%");
		return query.list();
	}

	public Meeting findById(long id) {
		return session.getSession().get(Meeting.class, id);
	}

	public Meeting add(Meeting meeting) {
		Transaction transaction = session.getSession().beginTransaction();
		session.getSession().save(meeting);
		transaction.commit();
		return meeting;
	}

	public void update(Meeting meeting) {
		Transaction transaction = session.getSession().beginTransaction();
		session.getSession().merge(meeting);
		transaction.commit();
	}

	public void delete(Meeting meeting) {
		Transaction transaction = session.getSession().beginTransaction();
		session.getSession().delete(meeting);
		transaction.commit();
	}

}
