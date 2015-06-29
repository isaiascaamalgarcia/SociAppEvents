require 'test_helper'

class UserControllerTest < ActionController::TestCase
  test "should get createUser" do
    get :createUser
    assert_response :success
  end

end
