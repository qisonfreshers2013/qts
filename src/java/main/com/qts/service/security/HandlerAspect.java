package com.qts.service.security;

import com.qts.common.EntityConstants;
import com.qts.exception.BusinessException;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.handler.AbstractHandler;
import com.qts.handler.annotations.AuthorizeCategory;
import com.qts.handler.annotations.AuthorizeEntity;
import com.qts.persistence.dao.DAOFactory;
import com.qts.service.common.ServiceRequestContext;
import com.qts.service.common.ServiceRequestContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author Vamsi Kuchi
 * 
 */

@Aspect
public class HandlerAspect {

	@Around("execution(* com.qts.handler.*.*AOP(..))"
			+ " && !cflowbelow(execution(* com.qts.handler.*.*AOP(..)))")
	public Object doCheck(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		checkIsUserAuthorized(thisJoinPoint);
		Object ob = thisJoinPoint.proceed();
		return ob;
	}

	private void checkIsUserAuthorized(ProceedingJoinPoint thisJoinPoint)
			throws Exception {
		Method method = ((MethodSignature) thisJoinPoint.getSignature())
				.getMethod();
		if (method.getAnnotation(AuthorizeEntity.class) != null) {
			checkIsUserEntityAuthorized(thisJoinPoint);
		} else if (method.getAnnotation(AuthorizeCategory.class) != null) {
			checkIsUserCategoryAuthorized(thisJoinPoint);
		} else {
			// if annotation is not present
		}
	}

	private void checkIsUserEntityAuthorized(ProceedingJoinPoint thisJoinPoint)
			throws BusinessException, ObjectNotFoundException {
		ServiceRequestContext context = ServiceRequestContextHolder
				.getContext();
		MethodSignature signature = (MethodSignature) thisJoinPoint
				.getSignature();

		AuthorizeEntity authorizeEntity = signature.getMethod().getAnnotation(
				AuthorizeEntity.class);
		String[] roles = authorizeEntity.roles();
		String action = authorizeEntity.action();
		String entity = authorizeEntity.entity();

		/*long affinityId = context.getAffinityId();
		long userRoleId = context.getUserSessionToken().getRoleId();

		DAOFactory daoFactory = DAOFactory.getInstance();
		long actionId = daoFactory.getActionDAO().getAction(action).getId();

		if (entity.equals(EntityConstants.AFFINITY_LITERAL)
				|| entity.equals(EntityConstants.AFFINITY_TYPE__LITERAL)) {
			affinityId = 0;
		}

		Role role = daoFactory.getRoleDAO().getRole(userRoleId);
		String roleName = role.getName();
		boolean roleStatus = roleCheck(roleName, roles);
		if (!roleStatus) {
			throw new BusinessException(ExceptionCodes.USER_NOT_AUTHORIZED,
					ExceptionMessages.USER_NOT_AUTHORIZED);
		}

		EntityPrivilege entityPrivilege = daoFactory.getEntityPrivilegeDAO()
				.getEntityPrivilege(entity, userRoleId, affinityId, actionId);

		if (entityPrivilege == null) {
			throw new BusinessException(ExceptionCodes.USER_NOT_AUTHORIZED,
					ExceptionMessages.USER_NOT_AUTHORIZED);
		}*/
	}

	private void checkIsUserCategoryAuthorized(ProceedingJoinPoint thisJoinPoint)
			throws BusinessException, ObjectNotFoundException {
		ServiceRequestContext context = ServiceRequestContextHolder
				.getContext();
		MethodSignature signature = (MethodSignature) thisJoinPoint
				.getSignature();

		AuthorizeCategory authorizeCategory = signature.getMethod()
				.getAnnotation(AuthorizeCategory.class);
		String[] roles = authorizeCategory.roles();
		String action = authorizeCategory.action();

		/*long affinityId = context.getAffinityId();
		long userRoleId = context.getUserSessionToken().getRoleId();
		long categoryId = context.getCategoryId();

		DAOFactory daoFactory = DAOFactory.getInstance();
		long engagementModelType = daoFactory.getCategoryDAO()
				.getCategory(categoryId).getEngagementModel();
		long engagementModelId = daoFactory.getEngagementModelDAO().getEngagementModelByType(engagementModelType).getId();

		String roleName = daoFactory.getRoleDAO().getRole(userRoleId).getName();

		boolean roleStatus = roleCheck(roleName, roles);
		if (!roleStatus) {
			throw new BusinessException(ExceptionCodes.USER_NOT_AUTHORIZED,
					ExceptionMessages.USER_NOT_AUTHORIZED);
		}

		long actionId = daoFactory.getActionDAO().getAction(action).getId();

		CategoryPrivilege categoryPrivilege = null;
		categoryPrivilege = daoFactory.getCategoryPrivilegeDAO()
				.getCategoryPrivilege(affinityId, categoryId, userRoleId,
						actionId, engagementModelId);

		if (categoryPrivilege == null) {
			categoryPrivilege = daoFactory.getCategoryPrivilegeDAO()
					.getCategoryPrivilege(affinityId,
							AbstractHandler.ALL_CATEGORIES, userRoleId,
							actionId, engagementModelId);

		}
		if (categoryPrivilege == null) {
			throw new BusinessException(ExceptionCodes.USER_NOT_AUTHORIZED,
					ExceptionMessages.USER_NOT_AUTHORIZED);
		}*/
	}

	private boolean roleCheck(String roleName, String[] roles) {
		for (String role : roles) {
			if (role.equals(roleName)) {
				return true;
			}
		}
		return false;
	}
}
