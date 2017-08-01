package edu.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import edu.domain.model.Beneficiary;
import edu.domain.model.Project;
import edu.domain.model.Resource;
import edu.domain.model.Task;
import edu.domain.model.TimeFrame;
import edu.domain.model.Volunteer;

public class App {
	private static Logger logger = Logger.getLogger(App.class);
	private static EntityManagerFactory emf;

	static {
		try {
			emf = Persistence.createEntityManagerFactory("volunteering_proj");
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) throws ParseException {

		fillDataBase();
		Scanner input = new Scanner(System.in);
		System.out.println("If you are Admin enter 'A' and if you are volunteer enetr 'V'");
		String user = input.nextLine();
		
		/* Volunteer enters his offer*/
		if (user.equalsIgnoreCase("V")) {

			EntityManager em = null;
			EntityTransaction tx = null;

			try {
				em = emf.createEntityManager();
				tx = em.getTransaction();
				tx.begin();
				Volunteer volunteer = new Volunteer();
				System.out.println("Enter your Name");
				volunteer.setName(input.nextLine());
				System.out.println("Enter your Skill");
				volunteer.setSkill(input.nextLine());

				em.persist(volunteer);

				tx.commit();
				System.out.print("Thank you for joining");

			} catch (PersistenceException e) {
				if (tx != null) {
					System.err.println("Rolling back" + e);
					tx.rollback();
				}
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}

		else if (user.equalsIgnoreCase("A")) {
			boolean flag = true;
			while (flag) {
				System.out.println("******************************************************************");

				System.out.println("Enter 'L' to see the List information about projects and their beneficiaries \n"
						+ "Enter 'T' to see List tasks for a project \n"
						+ "Enter 'SS' to see List projects by status \n"
						+ "Enter 'R'to see Look for projects that requires a particular type of resource \n"
						+ "Enter 'S' to see Search projects by keywords and location \n"
						+ "Enter 'v' to see List projects and tasks where a volunteer have offered services, ordered by date of the task");

				System.out.println("********************************************************************/n");

				String key = input.nextLine();
				switch (key) {

				case "L":
					listProjInfo();					
					
				case "T":
					projTasks();					
					break;
				case "SS":
					projByStatus();					
					
				case "R":
					projByResource();					
					
				case "S":
					projByLocation();					
					
				case "V":
					listProjInfo();
					
					break;
				default:
					System.err.println("Enter Correctly");
					
				}				
				System.out.println("enter 'N' if you want quit");
				if (input.nextLine().equalsIgnoreCase("n")){
					flag = false;
					input.close();
			}
			
			}
		}
	}

	/** fills sample data*/
	public static void fillDataBase() throws ParseException {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			TimeFrame timeFrame_proj = new TimeFrame();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			Date proj_str_date = fmt.parse("2017-05-06");
			Date proj_end_date = fmt.parse("2018-05-03");

			Beneficiary beneficiary1 = new Beneficiary();
			Beneficiary beneficiary2 = new Beneficiary();
			Beneficiary beneficiary3 = new Beneficiary();

			timeFrame_proj.setStartDate(proj_str_date);
			timeFrame_proj.setEndDates(proj_end_date);
			Project proj1 = new Project("addis", timeFrame_proj);
			Project proj2 = new Project("DC", timeFrame_proj);

			beneficiary1.setName("Esays");
			beneficiary1.setProject(proj1);
			beneficiary2.setName("joe");
			beneficiary2.setProject(proj1);
			beneficiary3.setName("bob");
			beneficiary3.setProject(proj2);

			Task task1 = new Task("ToDo", timeFrame_proj, proj1);
			Task task2 = new Task("In_Progress", timeFrame_proj, proj1);
			Task task3 = new Task("In_Progress", timeFrame_proj, proj2);

			Resource resource1 = new Resource("devloper", "exsiting project", task1);
			Resource resource2 = new Resource("driver", "exsiting project", task2);
			Resource resource3 = new Resource("devloper", "exsiting project", task3);

			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			em.persist(resource1);
			em.persist(resource2);
			em.persist(resource3);
			em.persist(beneficiary1);
			em.persist(beneficiary2);
			em.persist(beneficiary3);

			tx.commit();

		} catch (PersistenceException e) {
			if (tx != null) {
				System.err.println("Rolling back" + e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**shows the List information about projects and their beneficiaries */
	private static void listProjInfo() {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			System.out.println("**********List all the project beneficiaries:*********");

			List<Project> projs = em.createQuery("Select DISTINCT proj from Project proj").getResultList();

			for (Project proj : projs) {
				Query query = em.createQuery(
						"Select DISTINCT ben from Beneficiary ben join Fetch ben.project bPro where bPro.id = :id ");
				query.setParameter("id", proj.getId());
				List<Beneficiary> bens = query.getResultList();
				logger.trace(String.format("%-18s%-31s%-31s%-31s", "Project_ID:", "Project_location:", "start_date:",
						"end_date"));

				logger.trace(String.format("%-18s  %-18s %18s %31s", proj.getTimeframe().getStartDate(),
						proj.getLocation(), proj.getTimeframe().getStartDate(), proj.getTimeframe().getEndDates()));

				logger.trace(String.format("%-18s%-31s", "Beneficiary_Id:", "Beneficiary_Name"));
				for (Beneficiary ben : bens) {
					logger.trace(String.format("%-18s%-18s", ben.getName(), ben.getName()));
				}
			}
			tx.commit();

			emf.close();
		} catch (PersistenceException e) {
			if (tx != null) {
				System.err.println("Rolling back:" + e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**shows list tasks for a project*/
	private static void projTasks() {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			System.out.println("List all the beneficiaries:");

			List<Project> projs = em.createQuery("Select DISTINCT proj from Project proj").getResultList();

			for (Project proj : projs) {

				Query query = em
						.createQuery("Select task From Task task join Fetch task.project tsk where tsk.id =:id");
				query.setParameter("id", proj.getId());
				List<Task> tasks = query.getResultList();

				logger.trace(String.format("%-18s%-31s%-31s%-31s", "Project_ID:", "Project_location:", "start_date:",
						"end_date"));
				logger.trace(String.format("%-18s  %-18s %18s %31s", proj.getTimeframe().getStartDate(),
						proj.getLocation(), proj.getTimeframe().getStartDate(), proj.getTimeframe().getEndDates()));

				logger.trace(String.format("%-18s%-31s", "Task_Id:", "Task_Status"));
				for (Task task : tasks) {
					logger.trace(String.format("%-18s%-18s", task.getId(), task.getStatus()));
				}
			}
			tx.commit();

			emf.close();
		} catch (PersistenceException e) {
			if (tx != null) {
				System.err.println("Rolling back:" + e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	/**shows the list projects by status*/
	private static void projByStatus() {
		EntityManager em = null;
		EntityTransaction tx = null;
		System.out.println("Enter status Keyword like 'ToDo' 'In_Progress'");
		Scanner input = new Scanner(System.in);
		String status = input.nextLine();

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			System.out.println("List all the projects by Status:");
			List<Task> tasks = em.createQuery("Select task From Task task").getResultList();

			for (Task task : tasks) {
				Query query = em.createQuery("Select DISTINCT task.project from Task task where task.status = :status");
				query.setParameter("status", status);
				List<Project> projs = query.getResultList();

				for (Project proj : projs) {
					logger.trace(String.format("%-18s%-31s%-31s%-31s", "Project_ID:", "Project_location:",
							"start_date:", "end_date"));
					logger.trace(String.format("%-18s  %-18s %18s %31s", proj.getTimeframe().getStartDate(),
							proj.getLocation(), proj.getTimeframe().getStartDate(), proj.getTimeframe().getEndDates()));

				}
			}
			tx.commit();

			emf.close();
		} catch (PersistenceException e) {
			if (tx != null) {
				System.err.println("Rolling back:" + e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		input.close();
	}
    
	/**shows the list projects by resource*/
	private static void projByResource() {
		EntityManager em = null;
		EntityTransaction tx = null;
		System.out.println("Enter Skill Keyword like 'devloper' and 'driver' ");
		Scanner input = new Scanner(System.in);
		String skill = input.nextLine();

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			System.out.println("List all the projects by Resource:");
			List<Resource> resources = em.createQuery("Select resource From Resource resource").getResultList();

			for (Resource resource: resources) {
				Query query = em.createQuery("Select DISTINCT resource.task.project from Resource resource where resource.skill = :skill");
				query.setParameter("skill", skill);
				List<Project> projs = query.getResultList();

				for (Project proj : projs) {
					logger.trace(String.format("%-18s%-31s%-31s%-31s", "Project_ID:", "Project_location:",
							"start_date:", "end_date"));
					logger.trace(String.format("%-18s  %-18s %18s %31s", proj.getTimeframe().getStartDate(),
							proj.getLocation(), proj.getTimeframe().getStartDate(), proj.getTimeframe().getEndDates()));

				}
			}
			tx.commit();

			emf.close();
		} catch (PersistenceException e) {
			if (tx != null) {
				System.err.println("Rolling back:" + e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		input.close();
	}

	/**shows the list projects by loaction*/
	private static void projByLocation() {
		EntityManager em = null;
		EntityTransaction tx = null;
		System.out.println("Enter Location Keyword like 'dc'");
		Scanner input = new Scanner(System.in);
		String location = input.nextLine();

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			System.out.println("List all the projects by Resource:");
			 Query query = em.createQuery("Select proj From Project proj Where proj.location = :location");
			 query.setParameter("location", location);
			 List<Project> projects = query.getResultList();
			for (Project proj: projects) {				
					logger.trace(String.format("%-18s%-31s%-31s%-31s", "Project_ID:", "Project_location:",
							"start_date:", "end_date"));
					logger.trace(String.format("%-18s  %-18s %18s %31s", proj.getTimeframe().getStartDate(),
							proj.getLocation(), proj.getTimeframe().getStartDate(), proj.getTimeframe().getEndDates()));
					}
			
			tx.commit();

			emf.close();
		} catch (PersistenceException e) {
			if (tx != null) {
				System.err.println("Rolling back:" + e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		input.close();
	}

}