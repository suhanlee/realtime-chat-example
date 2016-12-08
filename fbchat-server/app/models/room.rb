class Room < ApplicationRecord

  def join_users
    users_arr = JSON.parse(super).compact.delete_if { |item| item.eql?("")}
    users_arr.map do |user_id|
      user_id.to_i
    end
  end

  def users
    users_arr = join_users.compact.delete_if { |item| item.eql?("")}
    users_arr.map do |user_id|
      User.find(user_id)
    end
  end
end
