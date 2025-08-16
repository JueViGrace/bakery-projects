import { toast } from 'sonner';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { cn } from '@/lib/utils';
import { Button } from '@ui/ui/button';
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@ui/ui/form';
import { Input } from '@ui/ui/input';
import { format } from 'date-fns';
import { Popover, PopoverContent, PopoverTrigger } from '@ui/ui/popover';
import { Calendar } from '@ui/ui/calendar';
import { Calendar as CalendarIcon } from 'lucide-react';

const formSchema = z.object({
  first_name_field: z
    .string()
    .min(2, { error: 'First name must be longer thant 2 characters' })
    .max(255, { error: 'First name must not be longer than 255 characters' }),
  last_name_field: z
    .string()
    .min(2, { error: 'Last name must be longer thant 2 characters' })
    .max(255, { error: 'Last name must not be longer than 255 characters' }),
  email_field: z.email(),
  phone_number_field: z.string(),
  birth_date_field: z.date(),
  username_field: z
    .string()
    .min(4, { error: 'Username must be longer thant 4 characters' })
    .max(255, { error: 'Usernam must not be longer than 255 characters' })
    .optional(),
  password_field: z
    .string()
    .min(2, { error: 'Password must be longer thant 2 characters' })
    .max(255, { error: 'Password must not be longer than 255 characters' }),
  confirm_password_field: z
    .string()
    .min(4, { error: 'Password must be longer thant 2 characters' })
    .max(255, { error: 'Password must not be longer than 255 characters' }),
});

type Schema = z.infer<typeof formSchema>;

export default function MyForm() {
  const form = useForm<Schema>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      birth_date_field: new Date(),
    },
  });

  function onSubmit(values: z.infer<typeof formSchema>) {
    try {
      console.log(values);
      toast(
        <pre className="mt-2 w-[340px] rounded-md bg-slate-950 p-4">
          <code className="text-white">{JSON.stringify(values, null, 2)}</code>
        </pre>
      );
    } catch (error) {
      console.error('Form submission error', error);
      toast.error('Failed to submit the form. Please try again.');
    }
  }

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="mx-auto max-w-3xl space-y-8 py-10"
      >
        <div className="grid grid-cols-12 gap-4">
          <div className="col-span-6">
            <FormField
              control={form.control}
              name="first_name_field"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>First Name</FormLabel>
                  <FormControl>
                    <Input placeholder="First name..." type="text" {...field} />
                  </FormControl>
                  <FormDescription>Type here your first name.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <div className="col-span-6">
            <FormField
              control={form.control}
              name="last_name_field"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Last name</FormLabel>
                  <FormControl>
                    <Input placeholder="Last name..." type="text" {...field} />
                  </FormControl>
                  <FormDescription>Type here your last name.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
        </div>

        <div className="grid grid-cols-12 gap-4">
          <div className="col-span-4">
            <FormField
              control={form.control}
              name="email_field"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <Input placeholder="Email..." type="email" {...field} />
                  </FormControl>
                  <FormDescription>Type here your email.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <div className="col-span-4">
            <FormField
              control={form.control}
              name="phone_number_field"
              render={({ field }) => (
                <FormItem className="flex flex-col items-start">
                  <FormLabel>Phone number</FormLabel>
                  <FormControl className="w-full">
                    <Input
                      type="tel"
                      placeholder="Phone number..."
                      {...field}
                      // defaultCountry="TR"
                    />
                  </FormControl>
                  <FormDescription>Enter your phone number.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <div className="col-span-4">
            <FormField
              control={form.control}
              name="birth_date_field"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>Date of birth</FormLabel>
                  <Popover>
                    <PopoverTrigger asChild>
                      <FormControl>
                        <Button
                          variant={'outline'}
                          className={cn(
                            'w-[240px] pl-3 text-left font-normal',
                            !field.value && 'text-muted-foreground'
                          )}
                        >
                          {field.value ? (
                            format(field.value, 'PPP')
                          ) : (
                            <span>Pick a date</span>
                          )}
                          <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                        </Button>
                      </FormControl>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0" align="start">
                      <Calendar
                        mode="single"
                        selected={field.value}
                        onSelect={field.onChange}
                        initialFocus
                      />
                    </PopoverContent>
                  </Popover>
                  <FormDescription>
                    Your date of birth is used to calculate your age.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
        </div>

        <FormField
          control={form.control}
          name="username_field"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Username</FormLabel>
              <FormControl>
                <Input placeholder="Username..." type="text" {...field} />
              </FormControl>
              <FormDescription>
                You can choose a username, if not your email will be used
                instead.
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />

        <div className="grid grid-cols-12 gap-4">
          <div className="col-span-6">
            <FormField
              control={form.control}
              name="password_field"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="Password..."
                      {...field}
                    />
                  </FormControl>
                  <FormDescription>Enter your password.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <div className="col-span-6">
            <FormField
              control={form.control}
              name="confirm_password_field"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Confirm password</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="Password..."
                      {...field}
                    />
                  </FormControl>
                  <FormDescription>Enter your password again.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
        </div>
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
}
