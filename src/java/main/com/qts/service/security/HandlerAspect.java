package com.qts.service.security;

import com.qsp.common.EntityConstants;
import com.qsp.exception.BusinessException;
import com.qsp.exception.ExceptionCodes;
import com.qsp.exception.ExceptionMessages;
import com.qsp.exception.ObjectNotFoundException;
import com.qsp.handler.AbstractHandler;
import com.qsp.handler.annotations.AuthorizeCategory;
import com.qsp.handler.annotations.AuthorizeEntity;
import com.qsp.model.CategoryPrivilege;
import com.qsp.model.EntityPrivilege;
import com.qsp.model.Role;
import com.qsp.persistence.dao.DAOFactory;
import com.qsp.service.common.ServiceRequestContext;
import com.qsp.service.common.ServiceRequestContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author Nikitha
 * 
 */

@Aspect
public class HandlerAspect {

	@Around("execution(* com.qsp.handler.*.*AOP(..))"
			+ " && !cflowbelow(execution(* com.qsp.handler.*.*AOP(..)))")
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

		long affinityId = context.getAffinityId();
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
		}
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

		long affinityId = context.getAffinityId();
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
		}
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
