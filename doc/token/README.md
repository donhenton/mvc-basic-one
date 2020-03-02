# Configure OKTA for role definitions

## Roles in Okta

Groups in Okta are what is available for roles, Assign users to groups following
the standard procedure outlined.

## Make Groups Available as Roles

Menu item API --> Authorization Servers --> Server (default), under this option:

### Scopes

add a group scope default scope and metadata publish


### Claims

![alt text][claims_list]

[claims_list]: claims_list.png "Claims Listing"

Add a claim called 'groups':
 

![alt text][groups]

[groups]: group_regex.png "Groups Regex"


This should make the token look like this:


![alt text][token_preview]

[token_preview]: token_preview.png "Groups Regex"