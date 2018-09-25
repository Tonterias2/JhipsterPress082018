# Problem 9: Create a new Authority

Let's imagine you need a new authority besides the ones given of ADMIN and USER.

You will end up modify the (src/main/java/location/security/)AuthoritiesConstants.java file:

package es3.security;

	/**
	 * Constants for Spring Security authorities.
	 */
	public final class AuthoritiesConstants {
	
	    public static final String ADMIN = "ROLE_ADMIN";
	
	    public static final String USER = "ROLE_USER";
	
	    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
	
	    private AuthoritiesConstants() {
	    }
	}

Do not forget to include it in your /src/main/resources/config/liquibase/authorities.csv:

	name
	ROLE_ADMIN
	ROLE_USER
	ROLE_ANONYMOUS


With that, you will be able to use in your /src/main/java/es3/config/SecurityConfiguration.java or in (src/main/java/com/jhipsterpress2/web/web/rest/FrontpageconfigResource.java) for example
	
	@DeleteMapping("/order-items/{id}")
	@Timed
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
	    ...
	}

and/or Angular files: jhiHasAnyAuthority=[‘ROLE_ADMIN’. ‘ROLE_X’ ……] or even consider to use it on the routes:

	export const messageRoute: Routes = [
	    {
	        path: 'message',
	        component: MessageComponent,
	        data: {
	            authorities: ['ROLE_USER'],
	            pageTitle: 'Messages'
	        },
	        canActivate: [UserRouteAccessService]
	    }
	];
