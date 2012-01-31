package org.jboss.as.quickstarts.servlet.async;

import ca.screenshot.chatservice.businesslayer.RoomService;
import ca.screenshot.chatservice.commonobjects.NewRoomMessageNotification;
import ca.screenshot.chatservice.commonobjects.Notification;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple service to simulate a long running task.
 *
 * @author Christian Sadilek <csadilek@redhat.com>
 */
@Stateless
public class LongRunningService {

	@Inject
	private RoomService roomService;

	private Logger logger = Logger.getLogger(LongRunningService.class.getName());

	/**
	 * The use of {@link Asynchronous} causes this EJB method to be executed
	 * asynchronously, by a different thread from a dedicated, container managed
	 * thread pool.
	 *
	 * @param asyncContext the context for a suspended Servlet request that this EJB will
	 *                     complete later.
	 * @param roomId
	 * @param userId
	 */
	@Asynchronous
	public void readData(AsyncContext asyncContext, String roomId, String userId) {
		try {
			PrintWriter writer = asyncContext.getResponse().getWriter();

			final Notification notification = roomService.pollNextNotification(roomId, userId);
			final JAXBContext jaxbContext = JAXBContext.newInstance(Notification.class, NewRoomMessageNotification.class);
			final Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.marshal(notification, writer);

			writer.close();

			asyncContext.complete();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}
}
